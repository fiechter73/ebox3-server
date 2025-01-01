package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.model.dto.MessageDTO;
import com.ebox3.server.repo.EBoxRepository;
import com.ebox3.server.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	EBoxRepository eBoxRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Iterable<EboxDTO> getFreeObjects() {
		List<Ebox> neweBoxList = new ArrayList<Ebox>();
		List<Ebox> list = eBoxRepository.findAll();
		List<Ebox> eboxList = list.stream()
				.filter(str -> str.getStatusText().equals("frei") || str.getStatusText().equals("gekündigt"))
				.sorted(Comparator.comparing(Ebox::getBoxNumber)).collect(Collectors.toList());

		eboxList.forEach(ebox -> {
			Ebox box = new Ebox();
			box.setBoxNumber(ebox.getBoxNumber());
			box.setBoxType(ebox.getBoxType());
			box.setStatusText(ebox.getStatusText().contentEquals("gekündigt") ? "verfügbar" : ebox.getStatusText());

			if (ebox.getTerminateDate() != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(ebox.getTerminateDate());
				calendar.add(Calendar.DATE, 1);
				box.setTerminateDate(calendar.getTime());
			} else {
				box.setTerminateDate(ebox.getTerminateDate());
			}
			box.setBeschreibung(ebox.getBeschreibung());
			box.setBemerkungen(ebox.getBemerkungen());
			neweBoxList.add(box);
		});
		return neweBoxList.stream().map(ebox -> mapper.map(ebox, EboxDTO.class)).collect(Collectors.toList());
	}
	
	@Override
	public MessageDTO sendEMail(String name, String email, String tel, String nachricht, String ids) {
		return sendEmail(name, email, tel, nachricht, ids);
	}
	
	private MessageDTO sendEmail(String name, String email, String tel, String nachricht, String ids) {
		MessageDTO msgDTO = new MessageDTO();
		try {
			SimpleMailMessage msg = new SimpleMailMessage();

			msg.setTo("claudia.rindlisbacher@einstellbox.ch", "juerg.fiechter@einstellbox.ch", "info@einstellbox.ch");
			msg.setFrom("info@einstellbox.ch");
			msg.setSubject("Anfrage Objektnummer: " + ids);
			msg.setText("Name: " + name + "\n" + "Email: " + email + "\n" + "Tel: " + tel + "\n" + "Nachricht: "
					+ nachricht);
			javaMailSender.send(msg);
			logger.info("Message send!");
			msgDTO.setMsg("Email gesendet!");
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			msgDTO.setMsg("Email konnte nicht gesendet werden.");
		}
		return msgDTO;
	}

}
