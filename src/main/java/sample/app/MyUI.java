package sample.app;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI(path="")
@Theme("valo")
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		TimeField timeField1 = new TimeField("23時まで");
		TimeField timeField2 = new TimeField("99時まで");
		timeField2.setMaxHour(99);
		setContent(
			new VerticalLayout(
				new FormLayout(
					timeField1,
					timeField2
				),
				new Button("表示", e -> {
					Notification.show(
						timeField1.getValue()
						+ " " +
						timeField2.getValue()
					);
				})
			)
		);
	}
}
