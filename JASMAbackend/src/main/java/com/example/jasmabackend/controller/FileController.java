package com.example.jasmabackend.controller;

import com.example.jasmabackend.entities.user.User;
import com.example.jasmabackend.repositories.UserRepository;
import com.example.jasmabackend.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final UserRepository userRepository;

    @PostMapping("/devapi/user/process_img_edit")
    public ResponseEntity savePhotoToUser(@RequestParam("userimage") MultipartFile multipartFile,
                                          Authentication authentication) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        // save image name to database:
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        user.setImageName(fileName);

        String uploadDir = "user-photos/" + user.getEmail();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        userRepository.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
