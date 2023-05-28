package com.example.usermanagement.KeywordService;

import com.example.usermanagement.persistence.dao.UserRepository;
import com.example.usermanagement.service.KeywordService;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordTrendTest {

    @Mock
    private UserRepository userRepository;
    private KeywordService keywordService = new KeywordService(userRepository);

    @Test
    /**
     * 키워드: "keyword{i}"
     * i: 1 ~ 50, "keyword{i}"가 i번 등장.
     * 상위 20개: "keyword50", "keyword49" ... "keyword31"
     */
    void getPopularKeywordCase1() {
        // given
        List<String> data = new ArrayList<>();
        Set<String> expected = new HashSet<>();
        for (int i =1; i<=50; i++) {
            String k = "keyword" + String.valueOf(i);
            if (i >= 31)
                expected.add(k);
            for (int j =0; j < i; j++)
                data.add(k);
        }

        // when
        List<String> actual = keywordService.getFrequentItems(data, 20);

        // then
        actual.forEach((keyword) -> assertTrue(expected.contains(keyword)));
    }

    @Test
    /**
     * 키워드: "keyword{i}"
     * i: 1 ~ 10, "keyword{i}"가 i번 등장
     * 상위 20개: "keyword10", "keyword9" ... "keyword1".
     * 20개가 되지 않는 경우에 대한 테스트
     */
    void getPopularKeywordCase2() {
        // given
        List<String> data = new ArrayList<>();
        Set<String> expected = new HashSet<>();
        for (int i =1; i<=10; i++) {
            String k = "keyword" + String.valueOf(i);
            expected.add(k);
            data.add(k);
        }

        // when
        List<String> actual = keywordService.getFrequentItems(data, 20);

        // then
        actual.forEach((keyword) -> assertTrue(expected.contains(keyword)));

    }
}
