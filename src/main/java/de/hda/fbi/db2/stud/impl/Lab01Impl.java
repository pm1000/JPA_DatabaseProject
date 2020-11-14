package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import java.util.List;

public class Lab01Impl extends Lab01Data {

    @Override
    public List<Question> getQuestions() {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }

    @Override
    public void loadCsvFile(List<String[]> additionalCsvLines) {

    }
}
