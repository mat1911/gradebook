package com.app.service;

import com.app.entity.Mark;
import com.app.entity.Student;
import com.app.entity.Subject;
import com.app.repository.impl.MarkRepository;
import com.app.service.impl.SubjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MarkService {

    private SubjectService subjectService = new SubjectService();
    private MarkRepository markRepository = new MarkRepository();

    public List<Mark> findByStudentAndSubject(Student selectedStudent, Subject selectedSubject) {
        List<Mark> marks = markRepository.findByStudentAndSubject(selectedStudent, selectedSubject);
        return marks.isEmpty() ? Collections.emptyList() : marks;
    }

    public Map<String, List<String>> findByStudent(Student selectedStudent) {

        return markRepository
                .findByStudent(selectedStudent)
                .stream()
                .collect(Collectors
                        .groupingBy(mark -> mark.getSubject().getName(),
                                Collectors.mapping(Mark::getMark, Collectors.toList())));
    }

    public void generateDocumentWithMarks(Student student) {

        if(student == null){
            throw new NullPointerException("Student is not selected!");
        }

        Map<String, List<String>> marks = findByStudent(student);
        marks = fillMarksWithAllSubjects(marks);

        List<List<String>> allMarks = marks.entrySet()
                .stream()
                .map(mark -> {

                    String marksAsSingleString = String.join("; ", mark.getValue());
                    List<String> result = new ArrayList<>(List.of(mark.getKey()));
                    result.add(marksAsSingleString);

                    return result;
                })
                .collect(Collectors.toList());

        String fileName = "Oceny_" + student.getName() + "_" + student.getSurname() + "_" + student.getGroup().getName() + ".pdf";
        String mainHeader = "Oceny " + student.getName() + " " + student.getSurname();
        List<String> headers = List.of("Przedmiot", "Ocena");

        PdfGenerator pdfGenerator = new PdfGenerator(fileName);
        pdfGenerator.addTable(mainHeader, headers, allMarks);
        pdfGenerator.generatePdf();
    }

    private Map<String, List<String>> fillMarksWithAllSubjects(Map<String, List<String>> marks){

        List<Subject> allSubjects = subjectService.findAll();

        for(Subject subject : allSubjects){
            marks.computeIfAbsent(subject.getName(), k -> List.of(" "));
        }

        return marks;
    }

}
