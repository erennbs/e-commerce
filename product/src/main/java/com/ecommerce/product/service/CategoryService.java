package com.ecommerce.product.service;

import com.ecommerce.product.dto.CategoryResponseDto;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryMapper::toDto).toList();
    }
}
