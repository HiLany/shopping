package com.lanyang.files.repositories;

import com.lanyang.files.domains.File;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lany on 2019/4/3.
 */
public interface FileRepository extends MongoRepository<File,String> {
}
