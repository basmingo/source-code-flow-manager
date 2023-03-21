package com.iprody.source.code.flow.manager.audit.logging.audit;

import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import org.springframework.stereotype.Component;

/**
 * Class to test advices invocations from Project Autid Logging aspect class.
 * There are only methods which are used for tests.
 */
@Component
class TestClassForProjectAuditLogging {

    /**
     * This function is a method with a Project Log Operation annotation.
     */
    @ProjectLogOperation
    public void methodWithProjectLogOperationAnnotation() {
    }

    /**
     * This function is a method with a Project Log Operation annotation.
     *
     * @param gitProject the {@code GitProject} test object
     */
    @ProjectLogOperation
    public void methodWithProjectLogOperationAnnotation(GitProject gitProject) {
    }

    @ProjectLogOperation
    public void methodWithProjectLogOperationAnnotation(Object arg) {
    }

    /**
     * This function is a method with a Project Log Operation annotation.
     *
     * @param gitProject the {@code GitProject} test object
     * @param testValue  test object
     */
    @ProjectLogOperation
    public void methodWithProjectLogOperationAnnotation(GitProject gitProject, String testValue) {
    }

    /**
     * This function is a method with a Project Log Operation annotation.
     *
     * @param gitProject the {@code GitProject} test object
     * @throws Exception always throws an exception
     */
    @ProjectLogOperation
    public void methodWithProjectLogOperationAnnotationThrowException(GitProject gitProject) throws Exception {
        throw new Exception("Something went wrong");
    }

}
