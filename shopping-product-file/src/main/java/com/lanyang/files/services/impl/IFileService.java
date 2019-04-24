package com.lanyang.files.services.impl;

import com.lanyang.files.domains.File;
import com.lanyang.files.repositories.FileRepository;
import com.lanyang.files.services.FileService;
import com.shopping.core.dto.PageDto;
import com.shopping.core.dto.PageQueryDto;
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

    @Override
    public PageDto<File> findFileByPage(PageQueryDto<File> pageQueryDto) throws Exception{
        Pageable pageable;
        int page = (pageQueryDto.getCurrent()==0 ||pageQueryDto.getCurrent() ==1)?0:(pageQueryDto.getCurrent()-1);
        Sort.Direction order = Sort.Direction.DESC;
        if("".equals(pageQueryDto.getSortOrder()) || "".equals(pageQueryDto.getSortField())){
            pageable = new PageRequest(page,pageQueryDto.getPageSize());
        }else
            pageable = new PageRequest(page,pageQueryDto.getPageSize(),order,pageQueryDto.getSortField());//默认根据上传时间降序排列
        Page<File> filePage = fileRepository.findAll(pageable);
        List<File> files = filePage.getContent();
        for(File file :files){
            file.setContent(null);
        }
        return new PageDto(files,filePage.getTotalElements(),filePage.getTotalPages(),pageQueryDto.getCurrent());
//        return fileRepository.findAll(pageable);
    }
}
