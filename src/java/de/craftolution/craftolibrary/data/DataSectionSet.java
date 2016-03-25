package de.craftolution.craftolibrary.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.03.2016
 */
public class DataSectionSet implements Serializable {

	private static final long serialVersionUID = -6128169951769556038L;

	private final List<DataSection> sections = new ArrayList<>();
	private final List<String> sectionClasses = new ArrayList<>();

	DataSectionSet() { }

	void insertSection(final Class<? extends Data> dataClass, final DataSection section) {
		Check.nonNulls("The dataClass/section cannot be null!", dataClass, section);
		this.sections.add(section);
		this.sectionClasses.add(dataClass.getName());
	}

	int size() { return this.sectionClasses.size(); }

	DataSection getSection(final int index) { return this.sections.get(index); }

	String getSectionClass(final int index) { return this.sectionClasses.get(index); }

}