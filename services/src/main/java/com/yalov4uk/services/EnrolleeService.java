package com.yalov4uk.services;

import com.yalov4uk.abstracts.BaseService;
import com.yalov4uk.beans.Faculty;
import com.yalov4uk.beans.Subject;
import com.yalov4uk.beans.SubjectName;
import com.yalov4uk.beans.User;
import com.yalov4uk.exceptions.ServiceUncheckedException;
import com.yalov4uk.interfaces.IEnrolleeService;
import com.yalov4uk.interfaces.IFacultyDao;
import com.yalov4uk.interfaces.ISubjectDao;
import com.yalov4uk.interfaces.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by valera on 5/3/17.
 */
@Service
public class EnrolleeService extends BaseService implements IEnrolleeService {

    private final ISubjectDao subjectDao;
    private final IFacultyDao facultyDao;
    private final IUserDao userDao;

    @Autowired
    public EnrolleeService(ISubjectDao subjectDao, IFacultyDao facultyDao, IUserDao userDao) {
        this.subjectDao = subjectDao;
        this.facultyDao = facultyDao;
        this.userDao = userDao;
    }

    public List<SubjectName> getRequiredSubjectNames(User user, Faculty faculty) {
        try {
            if (faculty == null || faculty.getRegisteredUsers().contains(user)) {
                return null;
            }
            List<SubjectName> subjectNames = new LinkedList<>();
            faculty.getRequiredSubjects()
                    .forEach(requiredSubject -> {
                        if (user.getSubjects()
                                .stream()
                                .map(Subject::getSubjectName)
                                .noneMatch(subjectName -> subjectName.equals(requiredSubject))) {
                            subjectNames.add(requiredSubject);
                        }
                    });
            logger.info("got required subject names");
            return subjectNames;
        } catch (Exception e) {
            logger.error("not got required subject names");
            throw new ServiceUncheckedException(e);
        }
    }

    public Boolean registerToFaculty(User user, Faculty faculty, List<SubjectName> subjectNames,
                                     List<Integer> values) {
        try {
            if (faculty == null) {
                return false;
            }
            for (int i = 0; i < subjectNames.size() && i < values.size(); i++) {
                Subject subject = new Subject(values.get(i));
                subject.setSubjectName(subjectNames.get(i));
                subject.setUser(user);
                subjectDao.persist(subject);

                user.getSubjects().add(subject);
                userDao.update(user);
            }
            faculty.getRegisteredUsers().add(user);
            facultyDao.update(faculty);
            logger.info("registered to faculty");
            return true;
        }  catch (Exception e) {
            logger.error("not registered to faculty");
            throw new ServiceUncheckedException(e);
        }
    }
}
