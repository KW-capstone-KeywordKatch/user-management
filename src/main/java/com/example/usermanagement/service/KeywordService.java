package com.example.usermanagement.service;

import com.example.usermanagement.persistence.dao.UserRepository;
import com.example.usermanagement.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final UserRepository userRepository;

    /**
     * 사용자들이 많이 설정한 키워드 상위 20개 조회
     */
    public List<String> getPopularKeywords() {

        Optional<List<User>> optionalUsers = userRepository.selectEveryUsers();
        // 해당 메서드는 인증/인가가 완료된 상태, 즉 회원이 최소 1명이 존재하는 경우에만 호출된다.
        List<User> users = optionalUsers.get();
        List<String> keywords = new ArrayList<>();
        users.stream().forEach((u) -> keywords.addAll(u.getInterests()));
        return getFrequentItems(keywords, 20);
    }

    /**
     * 모든 키워드들에서 자주 등장한 키워드를 선별한다.
     * @param keywords 모든 회원들이 등록한 키워드. 중복되는 키워드가 존재한다.
     * @param limit 선별할 키워드 개수. included
     * @return 자주 등장한 상위 limit개의 키워드 리스트
     */
    public List<String> getFrequentItems(List<String> keywords, int limit) {

        Map<String, Integer> keywordsMap = new HashMap<>();

        for (String keyword: keywords)
            keywordsMap.put(keyword, keywordsMap.getOrDefault(keyword, 1) + 1);

        List<String> result = new ArrayList<>(keywordsMap.keySet());
        // 등장 횟수를 기준으로 키워드를 내림차순 정렬
        result.sort((k1, k2) -> keywordsMap.get(k2).compareTo(keywordsMap.get(k1)));
        if (result.size() > limit)
            result = result.subList(0, limit);
        return result;
    }
}
