/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.CategoryRepository;
import DTO.Category;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lelin
 */
public class CategoryService {
    private CategoryRepository categoryRepository = new CategoryRepository();
    
    public List<Category> getAllCategories() {
            return categoryRepository.getAllData();
    }
    public Map<Integer, String> getCategoryIdNameMap(){
        return categoryRepository.getCategoryIdNameMap();
    }
    
    public Category getCategoryById(int id){
        return categoryRepository.getCategoryById(id);
    }
    public boolean insertCategory(Category category)
    {
        return categoryRepository.insertData(category);
    }
    
    public boolean UpdateCategory(Category category)
    {
        return categoryRepository.updateData(category);
    }
    
    public boolean DeleteCategory(int id)
    {
        return categoryRepository.deleteData(id);
    }
}
