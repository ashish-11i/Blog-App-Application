package com.codewithashish.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
	
	private Integer categoryId;
	@NotBlank
	@Size(min = 4)
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10)
	private String categoryDescription;
}
