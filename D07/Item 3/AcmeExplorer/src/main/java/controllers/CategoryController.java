/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.TripService;
import domain.Category;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private TripService		tripService;


	// Constructors -----------------------------------------------------------

	public CategoryController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer categoryId) {
		ModelAndView result;
		Collection<Category> categories;
		Category category;
		Boolean hasParent;
		Integer tripNum;

		if (categoryId == null) {
			category = this.categoryService.findFirst();
			hasParent = false;
		} else {
			category = this.categoryService.findOne(categoryId);
			hasParent = category.getFather() != null;
		}

		categories = this.categoryService.findChildren(category.getId());
		tripNum = this.tripService.findByCategory(category).size();

		result = new ModelAndView("category/list");
		result.addObject("category", category);
		result.addObject("hasParent", hasParent);
		result.addObject("categories", categories);
		result.addObject("tripNum", tripNum);
		result.addObject("requestURI", "category/list.do?" + category.getId());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer categoryId) {
		ModelAndView result;
		Category father;
		Category category;

		category = new Category();
		father = this.categoryService.findOne(categoryId);
		category.setFather(father);

		result = this.createEditModelAndView(category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);
		result = this.createEditModelAndView(category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "category.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Category category) {
		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category, "folder.commit.error");
			oops.printStackTrace();
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;
		Collection<Category> categories;

		final String requestURI = "category/edit.do";
		categories = this.categoryService.findAll();
		categories.remove(category.getFather());
		categories.remove(category);

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("categories", categories);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
