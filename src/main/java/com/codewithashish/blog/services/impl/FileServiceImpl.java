package com.codewithashish.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codewithashish.blog.services.FileService;


@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws Exception {
		
		//File Name
		String fileName = file.getOriginalFilename();
		
		//Random Id generator
		String randomID = UUID.randomUUID().toString();
		
		String fileName1 = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		
		//Full path
		String filePath = path + File.separator + fileName1;
		
		//create folder if not exist
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		

		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullPath = path + File.separator + fileName;
		InputStream  is = new FileInputStream(fullPath);
		return is;
	}

}
