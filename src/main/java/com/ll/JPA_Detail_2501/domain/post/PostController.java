package com.ll.JPA_Detail_2501.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    /*
    * Lock수준을 어디까지 잡아줘야할까?
    *
    * 예를들어, 사용이력, 정산기준정보 두 가지 테이블이 있다고 하자.
    * AWS나 Google같은 경우 사용자가 수천만, 어쩌면 수억일테니
    * 매달 정산시에 사용이력을 조회하는데도 수십분이 걸릴것이다.
    * 그럼 사용이력과 정산기준정보를 Join해서 계산하게 됥텐데,
    * 어느 수준의 Lock을 제공해야할까?
    *
    * 그리고 DB내부에서 어느 수준의 Lock을 제공하느냐도 중요할것이다.
    * 예를들어, LGU+정산때, 수백의 파트너사들이 세금계산서와 전표테이블을
    * 조회하고, 발송하지만,  그들은 자신들의 파트너코드에 해당하는,
    * 그리고 권한수준에 해당하는 레코드만 조회하게 될 것이다.
    *
    * 그런 경우라면,  세금서와 전표테이블 자체를 Lock거는거보다,
    * 레코드 단위로 Lock을 걸면 훨씬 많은 트랜젝션을 충돌없이 처리할 수
    * 있을것이다.
    *
    * */

    private final PostService postService;

    @GetMapping("/{id}")
    public Post showPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/findByUsername/{username}")
    public List<Post> findByUsername(@PathVariable("username") String username) {
        return postService.findByUsername(username);
    }

    //DB 읽기 Lock(ShareLock)  테스트. 읽기 Lock걸린 대상은 Read시 UD가 불가하다.
    @GetMapping("/findWithShareLockById/{id}")
    public Post findWithShareLockById(@PathVariable Long id) {
        return postService.findWithShareLockById(id).orElse(null);
    }

    //DB 쓰기 Lock  테스트. 쓰기 Lock은 UD걸린 대상에 대한 조회가 불가하다.
    @GetMapping("/findWithWriteLockById/{id}")
    public Post findWithWriteLockById(@PathVariable Long id) {
        return postService.findWithWriteLockById(id).orElse(null);
    }

    /* 낙관적 Lock 테스트 - 트랜젝션 충돌이 안생기는걸 가정.
      개나소나 모든 트랜젝션이 접근하지만, 먼저 완성하는 놈의
      version이나 id 컬럼값이 업데이트되어,  나머지 대상들이
      version이나 id 컬럼을 조건으로 CUD하려고 하면,
     이미 완료된 트랜젝션이 해당 컬럼의 값을 바꾼고로, 실행되지 않는다.

      아래 url을 3번정도 호출하면 id = 3이되는데
      도중에 dbeaver에서 업데이트 쳐버리면 낙관에러 발생한다.
      ObjectOptimisticLockingFailureException: Row was updated or deleted by another transactio
     */
    @GetMapping("/modify/optimistic/{id}")
    public Post modifyOptimistic(@PathVariable Long id) {
        return postService.modifyOptimistic(id);
    }

}
