package com.lanyang.files.services.impl;

import com.lanyang.files.domains.File;
import com.lanyang.files.repositories.FileRepository;
import com.lanyang.files.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lany on 2019/4/3.
 */
@Service
public class IFileService implements FileService{

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    public void removeFile(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public File getFileById(String id) {
        return fileRepository.findById(id).get();
    }

    @Override
    public List<File> listFilesByPage(int page, int size) {
        Page<File> files = listFilePagesByPage(page,size);
        if(null == files){
            return null;
        }
        return files.getContent();


    }

    @Override
    public Page<File> listFilePagesByPage(int page, int size) {
        Page<File> filePage = null;

        Sort sort = new Sort(Sort.Direction.DESC,"uploadDate");

        Pageable pageable = new PageRequest(page,size,sort);

        filePage = fileRepository.findAll(pageable);

        return filePage;
    }
}
