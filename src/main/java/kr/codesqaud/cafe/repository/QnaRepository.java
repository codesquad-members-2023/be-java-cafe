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
    private static final Map<Integer, Qna> store = new ConcurrentHashMap<>();

    public void save(Qna qna) {

        int size = store.isEmpty() ? 0 : store.size();
        qna.setQnaIndex(size);
        store.put(size, qna);
    }

    public List<Qna> findAllQnas() {
        return new ArrayList<>(store.values());
    }

    public Qna findByQnaIndex(Integer index) {
        Qna qna = store.get(index);
        return qna;
    }

}
