package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ggroup.demo.model.Category;
import io.ggroup.demo.model.ErrorResponse;
import io.ggroup.demo.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category API", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // GET /api/categories - Get all categories

    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "No categories found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No categories found"));
        } else {
            return ResponseEntity.ok(categories);
        }
    }

    // GET /api/categories/{id} - Get category by ID

    @Operation(summary = "Get category by ID", description = "Returns a single category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id)
                .map(category -> ResponseEntity.ok((Object) category))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Category not found with ID: " + id)));
    }

    // POST /api/categories - Create a new category

    @Operation(summary = "Create a new category", description = "Creates a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            if (categoryRepository.existsById(category.getCategory())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Category already exists"));
            }
            Category savedCategory = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid category data: " + e.getMessage()));
        }
    }

    // PUT /api/categories/{id} - Update an existing category

    @Operation(summary = "Update an existing category", description = "Updates the details of an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Category not found with ID: " + id));
        }

        if (category.getCategory() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Category name is required"));
        }

        existingCategory.setCategory(category.getCategory());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    // DELETE /api/categories/{id} - Delete category by ID

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Deletes a category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted category with id {id}\"}"))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> deleteCategoryById(@PathVariable String id) {

        if (categoryRepository.existsById(id)) {
            try {
                categoryRepository.deleteById(id);
                return ResponseEntity.ok(Map.of("message", "Successfully deleted category with id " + id));
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Cannot delete category"));
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Category not found"));
        }
    }

}