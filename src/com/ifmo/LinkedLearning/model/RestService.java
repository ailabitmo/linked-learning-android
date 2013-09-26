package com.ifmo.LinkedLearning.model;

import com.foxykeep.datadroid.service.RequestService;
import com.ifmo.LinkedLearning.model.operations.CourseOperation;
import com.ifmo.LinkedLearning.model.operations.LectureOperation;
import com.ifmo.LinkedLearning.model.operations.ModuleOperation;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 25.09.13
 * Time: 0:14
 * To change this template use File | Settings | File Templates.
 */
public class RestService extends RequestService {

    @Override
    public Operation getOperationForType(int requestType) {
        switch (requestType) {
            case RequestFactory.REQUEST_MODULES:
                return new ModuleOperation();
            case RequestFactory.REQUEST_COURSES:
                return new CourseOperation();
            case RequestFactory.REQUEST_LECTURE:
                return new LectureOperation();
            default:
                return null;
        }
    }
}
