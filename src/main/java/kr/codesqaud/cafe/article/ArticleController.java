package kr.codesqaud.cafe.article;

import static kr.codesqaud.cafe.global.config.Session.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.article.dto.ArticleDTO;
import kr.codesqaud.cafe.article.dto.ArticleUpdateDTO;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/article/submit")
	public String postArticle(@ModelAttribute @Valid ArticleDTO articleDto, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		articleDto.setId(session.getId());
		articleDto.setNickName(session.getNickName());
		articleService.post(articleDto);
		return "redirect:/";
	}

	@GetMapping("/article/{idx}")
	public String showDetailArticle(@PathVariable Long idx, Model model) {
		model.addAttribute("article", articleService.findArticleByIdx(idx));
		return "post/show";
	}

	@GetMapping("/article/update-form/{idx}")
	public String showUpdateForm(@PathVariable Long idx, Model model, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		model.addAttribute("article", articleService.validSessionIdAndArticleId(idx, session.getId()));
		model.addAttribute("idx", idx);
		return "post/updateForm";
	}

	@PutMapping("/article/submit/update-form/{idx}")
	public String updateArticle(@ModelAttribute @Valid ArticleUpdateDTO articleUpdateDto, @PathVariable Long idx) {
		articleUpdateDto.setIdx(idx);
		articleService.updateArticle(articleUpdateDto);
		return "redirect:/";
	}

	@DeleteMapping("/article/{idx}/delete")
	public String deleteArticle(@PathVariable Long idx, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		articleService.deleteArticleByIdx(idx, session.getId());
		return "redirect:/";
	}

}