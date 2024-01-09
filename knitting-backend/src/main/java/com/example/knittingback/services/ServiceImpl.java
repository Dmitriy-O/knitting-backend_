package com.example.knittingback.services;

import com.example.knittingback.entity.*;
import com.example.knittingback.model.*;
import com.example.knittingback.model.FileEntity;
import com.example.knittingback.model.ItemEntity;
import com.example.knittingback.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service


public class ServiceImpl implements Service {

    final private RepositoryCategory repositoryCategory;
    final private RepositoryItemEntity repositoryItemEntity;
    final private RepositoryOrder repositoryOrder;
    final private RepositoryClient repositoryClient;
    final private RepositoryImagePath repositoryImagePath;

    final private String FOLDER_PATH = "D:\\Projects\\Portfolio\\full-stack\\images\\";

    public ServiceImpl(RepositoryCategory repositoryCategory, RepositoryItemEntity repositoryItemEntity, RepositoryOrder repositoryOrder, RepositoryClient repositoryClient, RepositoryImagePath repositoryImagePath) {
        this.repositoryCategory = repositoryCategory;
        this.repositoryItemEntity = repositoryItemEntity;
        this.repositoryOrder = repositoryOrder;
        this.repositoryClient = repositoryClient;
        this.repositoryImagePath = repositoryImagePath;
    }

    @Override
    public Category createCategory(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(category, categoryEntity);
        repositoryCategory.save(categoryEntity);
        return category;
    }

    @Override
    public com.example.knittingback.entity.ItemEntity createItem(MultipartFile image, String description, String price, String name) throws IOException {
        com.example.knittingback.entity.FileEntity uploadImageService = uploadImageService(image);
        com.example.knittingback.entity.ItemEntity newItemEntity = com.example.knittingback.entity.ItemEntity.builder()
                .name(name)
                .price(BigDecimal.valueOf(Long.parseLong(price)))
                .image_ID(repositoryImagePath.findTopByName(image.getName()))
                .build();
        return newItemEntity;
    }

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId())
                .date(order.getDate()).build();
        repositoryOrder.save(orderEntity);
        return order;
    }

    @Override
    public Client createClient(Client client) {

        ClientEntity clientEntity = ClientEntity.builder()
                .name(client.getName())
                .build();
        repositoryClient.save(clientEntity);
        return client;
    }


    @Override
    public com.example.knittingback.entity.FileEntity uploadImageService(MultipartFile image) throws IOException {
        String filePath = FOLDER_PATH + image.getOriginalFilename();

        image.transferTo(Path.of(filePath));
        com.example.knittingback.entity.FileEntity fileEntity = repositoryImagePath.save(com.example.knittingback.entity.FileEntity.builder()
                        .name(image.getOriginalFilename())
                        .type(image.getContentType())
                        .filePath(filePath)
                        .build());

        return fileEntity;


    }

    @Override
    public FileEntity downloadImageService(long id) throws IOException {
        return null;
    }


    @Override
    public List<ItemEntity> get_All_Items() {
        List<com.example.knittingback.entity.ItemEntity> categoryEntities
                = repositoryItemEntity.findAll();
        List<ItemEntity> itemEntities = categoryEntities.stream()
                .map(item -> new ItemEntity().builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .imagePath(String.valueOf(item.getImage_ID()))
                        .build()).toList();

        return itemEntities;
    }

    @Override
    public List<FileEntity> getAllImages() {
        List<com.example.knittingback.entity.FileEntity> fileData = repositoryImagePath.findAll();
        List<FileEntity> imageList = fileData
                .stream()
                .map(photo -> FileEntity.builder()
                        .id(photo.getId())
                        .name(photo.getName())
                        .type(photo.getType())
                        .filePath(photo.getFilePath())
                        .build()).toList();
        return imageList;
    }

    @Override
    public boolean deleteModel(long id) {

        return false;
    }

    @Override
    public Optional<CategoryEntity> getCategoryEntityByID(long categoryID) {

        Optional<CategoryEntity> categoryById = repositoryCategory.findById(categoryID);
        return categoryById;
    }
}

