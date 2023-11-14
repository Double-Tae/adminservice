package com.tae.adminservice.service.post;


import com.tae.adminservice.domain.member.Member;
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
    public PostDetailResponseDto findByPostId(Long postId){

        /*
         * [REQUIRED LISTS]
         * throw NotFoundException
         * convert to DTO(Detail)
         * DTO's image path required
         */

        Post post =
                postRepository.findById(postId).orElseThrow();

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

}
