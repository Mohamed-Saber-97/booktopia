package repository;

import base.BaseRepository;
import model.Buyer;
import model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryRepository extends BaseRepository<Category, Long> {
    public CategoryRepository() {
        super(Category.class);
    }
}
