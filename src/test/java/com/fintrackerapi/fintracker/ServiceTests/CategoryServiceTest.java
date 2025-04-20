package com.fintrackerapi.fintracker.ServiceTests;

import com.fintrackerapi.fintracker.components.CategoryConverter;
import com.fintrackerapi.fintracker.services.CategoryService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * TEST CASES
 * - Test get categories not empty
 * - Test get categories empty
 */

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryConverter categoryConverter;

    @InjectMocks
    private CategoryService categoryService;
}
