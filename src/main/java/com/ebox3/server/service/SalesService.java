package com.ebox3.server.service;

import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.model.dto.MessageDTO;

public interface SalesService {

	public Iterable<EboxDTO> getFreeObjects();

	public MessageDTO sendEMail(String name, String email, String tel, String nachricht, String ids);
}
