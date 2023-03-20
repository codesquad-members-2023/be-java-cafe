package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Qna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class QnaRepository {

    private Logger log = LoggerFactory.getLogger(getClass());
    private static final Map<String, Qna> store = new ConcurrentHashMap<>();

    public void save(Qna qna) {
        String key = qna.getTitle();
        store.put(key, qna);
    }

    public List<Qna> findAllQnas() {
        return new ArrayList<>(store.values());
    }

    public Qna findByQnaTitle(String title) {
        Qna qna = store.get(title);
        return qna;
    }

}
