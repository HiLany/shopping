package com.lanyang.files.services;

import com.lanyang.files.domains.File;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lany on 2019/4/3.
 */
public interface FileService {

    File saveFile(File file);

    void removeFile(String id);

    File getFileById(String id);

    List<File> listFilesByPage(int page,int size);

    Page<File> listFilePagesByPage(int page,int size);


}
