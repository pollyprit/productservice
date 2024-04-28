package com.productservice.services;

import com.productservice.dtos.FakeStoreProductDto;
import com.productservice.exceptions.ProductDoesNotExistsException;
import com.productservice.models.Category;
import com.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class FakeStoreProductService implements EComProductService {
    private RestTemplate restTemplate;
    private static String PRODUCT_URL = "https://fakestoreapi.com/products";

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        String url = PRODUCT_URL;

        // if (limit != null && limit > 0 && limit < 20)
        //     url += "?limit=" + limit;

        FakeStoreProductDto productDto[] = restTemplate.getForObject(url, FakeStoreProductDto[].class);
        List<Product> allProducts = new ArrayList<Product>();

        for (int i = 0; i < productDto.length; ++i)
            allProducts.add(convertProductDtoToProduct(productDto[i]));

        return new PageImpl<>(allProducts);
    }

    @Override
    public Product getProduct(Long id) throws ProductDoesNotExistsException {
        FakeStoreProductDto productDto = restTemplate.getForObject(
                PRODUCT_URL + "/" + id,
                FakeStoreProductDto.class);

        if (productDto == null)
            throw new ProductDoesNotExistsException("Product with id " + id + " not found.");

        return convertProductDtoToProduct(productDto);
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeProductDto(product);
        FakeStoreProductDto response = restTemplate.postForObject(
                PRODUCT_URL, fakeStoreProductDto, FakeStoreProductDto.class);

        return convertProductDtoToProduct(response);
    }

    @Override
    public void removeProduct(Long id) {
        restTemplate.delete(PRODUCT_URL + "/" + id);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductDoesNotExistsException {
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeProductDto(product);

        FakeStoreProductDto response = restTemplate.patchForObject(PRODUCT_URL + "/" + id,
                fakeStoreProductDto, FakeStoreProductDto.class);

        return convertProductDtoToProduct(response);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductDoesNotExistsException {
        //restTemplate.put(PRODUCT_URL + "/" + id, fakeStoreProductDto);

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute(PRODUCT_URL + "/" + id,
                HttpMethod.PUT, requestCallback, responseExtractor);

        return convertProductDtoToProduct(response);
    }

    private Product convertProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();

        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());

        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDto convertProductToFakeProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        return fakeStoreProductDto;
    }
}
