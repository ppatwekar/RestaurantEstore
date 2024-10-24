package com.estore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.controller.ShoppingCartController;
import com.estore.api.estoreapi.model.FoodDish;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemDataFormat;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartItemFull;
import com.estore.api.estoreapi.model.ShoppingCartFiles.ShoppingCartResponseObj;
import com.estore.api.estoreapi.services.ShoppingCartService;

@Tag("Controller-tier")
public class ShoppingCartControllerTest {
        private ShoppingCartController shoppingCartController;
        private ShoppingCartService mockCartService;

        @BeforeEach
        public void setupShoppingCartController() {
                this.mockCartService = mock(ShoppingCartService.class);
                this.shoppingCartController = new ShoppingCartController(this.mockCartService);
        }

        @Test
        public void getShoppingCartTest() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 4),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                int testCustomerId = 88;
                when(this.mockCartService.getShoppingCart(testCustomerId)).thenReturn(scro);

                ResponseEntity<ShoppingCartResponseObj> response = this.shoppingCartController
                                .getShoppingCart(testCustomerId);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(scro, response.getBody());

        }

        @Test
        public void getShoppingCartTestNotFound() {

                int testCustomerId = 88;
                when(this.mockCartService.getShoppingCart(testCustomerId)).thenReturn(null);

                ResponseEntity<ShoppingCartResponseObj> response = this.shoppingCartController
                                .getShoppingCart(testCustomerId);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        }

        @Test
        public void updateShoppingCartTest() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                when(this.mockCartService.updateShoppingCart(shd, 77))
                                .thenReturn(scro);

                ResponseEntity<ShoppingCartResponseObj> resposne = this.shoppingCartController.updateShoppingCart(shd,
                                77);

                assertEquals(HttpStatus.OK, resposne.getStatusCode());
                assertEquals(scro, resposne.getBody());

        }

        @Test
        public void updateShoppingCartTestNotFound() {
                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                when(this.mockCartService.updateShoppingCart(shd, 77))
                                .thenReturn(null);

                ResponseEntity<ShoppingCartResponseObj> response = this.shoppingCartController.updateShoppingCart(shd,
                                77);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        }

        @Test
        public void getShoppingCartFoodDishesTest() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                int customerId = 77;

                when(this.mockCartService.getShoppingCart(customerId))
                                .thenReturn(scro);

                ResponseEntity<List<ShoppingCartItemFull>> response = this.shoppingCartController
                                .getShoppingCartFoodDishes(customerId);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(scif, response.getBody());
        }

        @Test
        public void getShoppingCartFoodDishesTestNotFound() {
                int customerId = 77;

                when(this.mockCartService.getShoppingCart(customerId))
                                .thenReturn(null);

                ResponseEntity<List<ShoppingCartItemFull>> response = this.shoppingCartController
                                .getShoppingCartFoodDishes(customerId);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void updateShoppingCartFoodDishesTest() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);
                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                int customerId = 77;
                when(this.mockCartService.updateShoppingCart(shd, customerId))
                                .thenReturn(scro);

                ResponseEntity<List<ShoppingCartItemFull>> response = this.shoppingCartController
                                .updateShoppingCartFoodDishes(shd, customerId);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(scif, response.getBody());
        }

        @Test
        public void updateShoppingCartFoodDishesTestNotFound() {
                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                int customerId = 77;
                when(this.mockCartService.updateShoppingCart(shd, customerId))
                                .thenReturn(null);

                ResponseEntity<List<ShoppingCartItemFull>> response = this.shoppingCartController
                                .updateShoppingCartFoodDishes(shd, customerId);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testGetShoppingCartFoodDishesArray() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 4),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                int testCustomerId = 88;
                when(this.mockCartService.getShoppingCart(testCustomerId)).thenReturn(scro);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController
                                .getShoppingCartFoodDishesArray(testCustomerId);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().length);
        }

        @Test
        public void testGetShoppingCartFoodDishesArrayNotFound() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 4),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                int testCustomerId = 88;
                when(this.mockCartService.getShoppingCart(testCustomerId)).thenReturn(null);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController
                                .getShoppingCartFoodDishesArray(testCustomerId);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testUpdateShoppingCartFoodDishesArray() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                when(this.mockCartService.updateShoppingCart(shd, 77))
                                .thenReturn(scro);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController.updateShoppingCartFoodDishesArray(shd,
                                77);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().length);
        }

        @Test
        public void testUpdateShoppingCartFoodDishesArrayNotFound() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                when(this.mockCartService.updateShoppingCart(shd, 77))
                                .thenReturn(null);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController.updateShoppingCartFoodDishesArray(shd,
                                77);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testAddShoppingCartFoodDishes() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                when(this.mockCartService.getShoppingCart(77))
                                .thenReturn(scro);
                when(this.mockCartService.updateShoppingCart(shd, 77))
                                .thenReturn(scro);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController
                                .addShoppingCartFoodDishes(shd, 77);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().length);
        }

        @Test
        public void testAddShoppingCartFoodDishesNotFound() {
                FoodDish dish = new FoodDish(99, "Fried Rice", 1.23, 10, "Recipe", "Description", "Imageurl");
                FoodDish dish2 = new FoodDish(100, "Deep Fried Rice", 3.12, 15, "Recipe", "Deep Description",
                                "Deep Imageurl");

                ShoppingCartItemFull[] arr = new ShoppingCartItemFull[] {
                                new ShoppingCartItemFull(dish, 56),
                                new ShoppingCartItemFull(dish2, 9)
                };

                List<ShoppingCartItemFull> scif = java.util.Arrays.asList(arr);
                ShoppingCartResponseObj scro = new ShoppingCartResponseObj(scif, 13);

                ShoppingCartItemDataFormat shd = new ShoppingCartItemDataFormat(99, 56);

                when(this.mockCartService.getShoppingCart(77))
                                .thenReturn(scro);
                when(this.mockCartService.updateShoppingCart(shd, 77))
                                .thenReturn(null);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController
                                .addShoppingCartFoodDishes(shd, 77);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testAddShoppingCartCustomizedDish() {
                FoodDish[] dishes = new FoodDish[1];
                dishes[0] = new FoodDish(-1, "custom dish", 1.23, 10, "Recipe", "Description", "Imageurl");

                when(this.mockCartService.addCustomFoodDish(dishes[0], 77))
                                .thenReturn(dishes);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController
                                .addShoppingCartCustomizedDish(dishes[0], 77);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(dishes, response.getBody());

        }

        @Test
        public void testAddShoppingCartCustomizedDishFail() {
                FoodDish[] dishes = new FoodDish[1];
                dishes[0] = new FoodDish(-1, "custom dish", 1.23, 10, "Recipe", "Description", "Imageurl");

                when(this.mockCartService.addCustomFoodDish(dishes[0], 77))
                                .thenReturn(null);

                ResponseEntity<FoodDish[]> response = this.shoppingCartController
                                .addShoppingCartCustomizedDish(dishes[0], 77);

                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        }

}
