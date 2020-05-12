package com.rusu.controller.DashBoard.AddController;

import com.rusu.domain.News;
import com.rusu.domain.User;
import com.rusu.repos.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/dashboard/add")
public class AddNewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("news")
    public String getNews(){
        return "/dashboard/add/news";
    }

    @PostMapping("news")
    public String addNews(@AuthenticationPrincipal User author,
                          @RequestParam String name,
                          @RequestParam String tag,
                          @RequestParam String text,
                          @RequestParam("image") MultipartFile image) throws IOException {
        News news = new News(name, tag, text, author);

        if (image != null && !image.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + image.getOriginalFilename();

            image.transferTo(new File(uploadPath + "/" + resultFileName));

            news.setImage(resultFileName);
        }
        //date , tag, view count
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        news.setDate(formatter.format(date));

        newsRepository.save(news);
        return "/dashboard/add/news";
    }
}
