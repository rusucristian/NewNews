package com.rusu.controller.DashBoard.EditController;

import com.rusu.domain.News;
import com.rusu.domain.User;
import com.rusu.repos.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/dashboard/edit")
public class NewsEditController {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("newsTable")
    public String editNewsTable(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("news", newsRepository.findByAuthor(user));
        return "/dashboard/edit/newsTable";
    }

    @GetMapping("news/{newsID}")
    public String editNews(@PathVariable("newsID") Long id, Model model){
        model.addAttribute("news", newsRepository.getOne(id));
        return "/dashboard/edit/news";
    }

    @GetMapping("news/delete/{newsID}")
    public String deleteNews(@AuthenticationPrincipal User user, @PathVariable("newsID") Long id, Model model){
        newsRepository.deleteById(id);
        model.addAttribute("news", newsRepository.findByAuthor(user));
        return "/dashboard/edit/newsTable";
    }

    @PostMapping("newsEdit/{id}")
    public String editN(@AuthenticationPrincipal User author,
                        @PathVariable("id") Long id,
                        @Valid News news,
                        @RequestParam("image") MultipartFile image,
                        BindingResult result,
                        Model model) throws IOException {
        if (result.hasErrors()) {
            news.setId(id);
            return "/dashboard/edit/userTable";
        }

        news.setAuthor(author);

        //de luat elementele news aparte si de lucfrat cu ele

        if (image != null && !image.getOriginalFilename().isEmpty()){

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
        return "redirect:/dashboard/edit/newsTable";
    }
}
