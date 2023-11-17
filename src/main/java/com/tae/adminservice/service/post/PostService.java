package com.tae.adminservice.service.post;


import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.order.ItemOrder;
import com.tae.adminservice.domain.post.InterestedPost;
import com.tae.adminservice.domain.post.Post;
import com.tae.adminservice.domain.post.PreviousPost;
import com.tae.adminservice.model.post.InterestRequestDto;
import com.tae.adminservice.model.post.PostCreateRequestDto;
import com.tae.adminservice.model.post.PostDetailResponseDto;
import com.tae.adminservice.repository.post.InterestedPostRepository;
import com.tae.adminservice.repository.post.PostRepository;
import com.tae.adminservice.repository.post.PreviousPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final InterestedPostRepository interestedPostRepository;
    private final PreviousPostRepository previousPostRepository;
    // private final MemberService memberService;

    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow();
    }
    public Post createPost(PostCreateRequestDto dto){
        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * memberService or Repository required
         */

        Post createdPost = dto.toEntity(new Member());
        postRepository.save(createdPost);

        return createdPost;
    }

    // specific: 게시물 Id를 통해 게시물 조회
    // util: 해당 게시물 상세내용 조회
    public PostDetailResponseDto fetchByPostId(Long postId){

        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * convert to DTO(Detail)
         * DTO's image path required
         */

        Post post = this.findById(postId);

        increaseView(post); // 조회수 증가

        PostDetailResponseDto responseDto
                = PostDetailResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getView())
                .interest(post.getInterest())
                .price(post.getPrice())
                .imagePath("IMAGE_PATH") //
                .memberId(post.getSeller().getMemberId())
                .memberName(post.getSeller().getName())
                .build();

        return responseDto;
    }

    // specific: 판매자 멤버 Id를 통해 Post 조회
    // util: 판매 중인 게시물 불러오기
    public List<Post> findBySellerId(String memberId) {

        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * convert to DTO
         */

        List<Post> sellerPosts =
                postRepository.findAllBySeller_MemberId(memberId);
        return sellerPosts;
    }


    // specific: 구매자 멤버 Id를 통해 구매한 previous post 조회
    // util: 구매한 게시물들 불러오기
    public List<PreviousPost> findByBuyerId(String memberId) {
        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * convert to DTO
         */

        List<PreviousPost> buyerPosts =
                previousPostRepository.findAllByBuyer_MemberId(memberId);

        return buyerPosts;
    }


    // specific: 현재 멤버의 Id를 통해 Interested Post 불러오기
    // util: 관심 표시한 게시물들 불러오기

    public List<InterestedPost> findByInterestMemberId(String memberId){
        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * convert to DTO
         */

        List<InterestedPost> interestedPosts =
                interestedPostRepository.findAllByInterestedMember_MemberId(memberId);

        return interestedPosts;
    }


    // specific: 키워드,가격 사용하여 Post 조회
    // util: 키워드,가격 필터링된 게시물들 불러오기
    public List<Post> findFilteredPosts(int minPrice, int maxPrice, String keyword, String region){
        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * convert to DTO
         */

        List<Post> filteredPosts = postRepository.findAllByFilter(minPrice, maxPrice, keyword, region);
        return filteredPosts;
    }

    // specific: Post를 set하여 view 값 변경
    // util: 게시물의 조회수 증가
    private void increaseView(Post post){
        post.setView(post.getView()+1);
    }

    // specific: Post를 불러와 interest 감소 후, Member와 Post의 InterstedPost 관계 삭제
    // util: 멤버가 게시물의 관심사를 취소하고, 관심수 감소
    public void reduceInterest(InterestRequestDto dto) {
        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         */

        InterestedPost interestedPost =
                interestedPostRepository.findByMemberAndPostId(dto.getMemberId(), dto.getPostId()); // 일치하는 관심 관계 찾기

        Post post = this.findById(dto.getPostId()); // 게시물 불러오기
        post.setInterest(Math.max(0,post.getInterest()-1)); // 관심 수 감소

        interestedPostRepository.delete(interestedPost); // 관계 삭제
        postRepository.save(post); // 게시물 update
    }

    // specific: Post를 불러와 interest 증가 후, Member와 Post의 InterstedPost 관계 생성
    // util: 멤버가 게시물의 관심사를 생성하고, 관심수 증가
    private void increaseInterest(InterestRequestDto dto) {
        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * memberService Required
         */

        Post post = this.findById(dto.getPostId()); // 게시물 불러오기
        post.setInterest(post.getInterest()+1);// 관심 수 증가

        Member member = memberService.findByMemberId(dto.getMemberId()); // 멤버 불러오기

        InterestedPost newInterestedPost =
                InterestedPost.builder()
                        .interestedMember(member)
                        .post(post)
                        .build(); // 관계 저장

        interestedPostRepository.save(newInterestedPost);
        postRepository.save(post); // 게시물 업데이트
    }


    public PreviousPost createPreviousPost(ItemOrder itemOrder){
        PreviousPost previousPost = itemOrder.convertToPreviousPost();
        return previousPostRepository.save(previousPost);
    }

}
