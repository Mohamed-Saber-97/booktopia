package repository;

import base.BaseRepository;
import model.Category;

public class CategoryRepository extends BaseRepository<Category, Long> {
    public CategoryRepository() {
        super(Category.class);
    }
}
