package isen.project.contact_app;

import isen.project.contact_app.db.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PersonValueFactory implements Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<Person, String> cellData) {
		return new SimpleStringProperty(cellData.getValue().getFirstName());
	}
}