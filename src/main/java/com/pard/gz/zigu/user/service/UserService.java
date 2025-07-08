package com.pard.gz.zigu.user.service;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.dto.PostPreviewDto;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.school.entity.School;
import com.pard.gz.zigu.school.repository.SchoolRepo;
import com.pard.gz.zigu.school.service.SchoolService;
import com.pard.gz.zigu.user.dto.UserInfoResDto;
import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final SchoolService schoolService;

    @Transactional
    public void signUpUser(UserSignUpReqDto userSignUpReqDto) {

        System.out.println(userSignUpReqDto.getStudentMail());

        boolean exists = userRepo.existsByStudentMail(userSignUpReqDto.getStudentMail());
        System.out.println("exists = " + exists);


        // 2) 학교 엔티티 확보 (찾거나 생성)
        School school = schoolService.findOrCreateByName(userSignUpReqDto.getSchoolName());

        String encodedPw = passwordEncoder.encode(userSignUpReqDto.getPassword()); // 해시화

        System.out.println(school.getSchoolName());

        User newUser = User.builder()
                .nickname(userSignUpReqDto.getNickname())
                .password(encodedPw)
                .school(school)
                .studentMail(userSignUpReqDto.getStudentMail())
                .postList(null)
                .borrowedList(null)
                .build();

        System.out.println("저장될 유저 : 닉넴 - " + newUser.getNickname() + ", school이름 - "+ newUser.getSchool().getSchoolName());
        userRepo.save(newUser);
        userRepo.flush(); // 이거 넣으면 터질 때 바로 예외 나옴
    }

    public List<PostPreviewDto> readUserPost(User user){
        List<Post> posts = user.getPostList();

        List<PostPreviewDto> postPreviewDtos = posts.stream()
                .map(post -> {// 1) 첫 번째 이미지 뽑기 (없으면 null)
                    Image firstImage = post.getImages()
                            .stream()
                            .findFirst()        // Optional<Image>
                            .orElse(null);      // 비어 있으면 null

                    // 2) DTO 빌드
                    return PostPreviewDto.builder()
                            .post_id(post.getId())
                            .post_fir_Image(firstImage)
                            .itemName(post.getItemName())
                            .category(post.getCategory())
                            .price_per_hour(post.getPricePerHour())
                            .price_per_day(post.getPricePerDay())
                            .build();
                })
                .toList();

        return postPreviewDtos; // list로 build후 return
    }

    public UserInfoResDto readUserInfo(User currentUser){
        return UserInfoResDto.builder()
                .nickname(currentUser.getNickname())
                .schoolName(currentUser.getSchool().getSchoolName())
                .studentMail(currentUser.getStudentMail())
                .build();
    }

    public void deleteUser(User currentUser){
        userRepo.deleteById(currentUser.getId());
    }

}
