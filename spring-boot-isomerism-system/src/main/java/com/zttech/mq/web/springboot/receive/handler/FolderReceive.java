package com.zttech.mq.web.springboot.receive.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.zttech.mq.web.springboot.common.entity.FolderReceiveItem;
import com.zttech.mq.web.springboot.common.util.DateUtils;

public class FolderReceive {

	public void process(FolderReceiveItem item, String msg) throws InterruptedException, IOException {
		String fileSaveRoute = item.getFileSaveRoute();
		
		Thread.sleep(1);
		String fileName = DateUtils.getNowTime();
		File fileDir = new File(fileSaveRoute);
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File file = new File(fileSaveRoute + "\\" + fileName + ".txt");
		FileOutputStream stream = new FileOutputStream(file, true);
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write(msg);
		bufferedWriter.close();
		writer.close();
		stream.close();
		bufferedWriter = null;
		writer = null;
		stream = null;
	}

}
