package com.axonivy.utils.estimator.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.estimator.WorkflowEstimator;
import com.axonivy.utils.estimator.model.EstimatedTask;

import ch.ivyteam.ivy.environment.IvyTest;
import ch.ivyteam.ivy.process.model.BaseElement;

@IvyTest
@SuppressWarnings("restriction")
public class FlowExampleBasicTest extends FlowExampleTest {

	private static BaseElement start;
	private static BaseElement newStart;
	private static BaseElement taskB;
	private static BaseElement taskC;
	private static final String PROCESS_NAME = "FlowExampleBasic";
	
	@BeforeAll
	public static void setup() {
		setup(PROCESS_NAME);
		start = graph.findByElementName("start");
		newStart = graph.findByElementName("NewStart");
		taskB = graph.findByElementName("Task B");
		taskC = graph.findByElementName("Task C");
	}

	@Test
	void shouldFindAllTasksAtStart() {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		List<EstimatedTask> estimatedTasks = workflowEstimator.findAllTasks(start);

		assertArrayEquals(Arrays.array("Task A", "Task C", "Task B"), getTaskNames(estimatedTasks));
	}

	@Test
	void shouldFfindAllTasksAtTaskB() {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findAllTasks(taskB);

		assertArrayEquals(Arrays.array("Task B"), getTaskNames(estimatedTasks));	
	}

	@Test
	void shouldFindAllTasksAtTaskC() {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findAllTasks(taskC);

		assertArrayEquals(Arrays.array("Task C", "Task B"), getTaskNames(estimatedTasks));
	}

	@Test
	void shouldFindAllTasksAtNewStart() {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findAllTasks(newStart);
	
		assertArrayEquals(Arrays.array("Task C", "Task B"), getTaskNames(estimatedTasks));
	}
	
	@Test
	void shouldFindTasksOnPathWithoutFlowNameAtStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findTasksOnPath(start);
	
		assertArrayEquals(Arrays.array("Task A"), getTaskNames(estimatedTasks));
	}
	
	@Test
	void shouldFindTasksOnPathWithoutFlowNameAtTaskB() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findTasksOnPath(taskB);
	
		assertArrayEquals(Arrays.array("Task B"), getTaskNames(estimatedTasks));
	}
	
	@Test
	void shouldFindTasksOnPathWithoutFlowNameAtTaskC() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findTasksOnPath(taskC);
	
		assertArrayEquals(Arrays.array("Task C", "Task B"), getTaskNames(estimatedTasks));
	}
	
	@Test
	void shouldFindTasksOnPathWithoutFlowNameAtNewStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		var estimatedTasks = workflowEstimator.findTasksOnPath(newStart);
	
		assertArrayEquals(Arrays.array("Task B"), getTaskNames(estimatedTasks));
	}
	
	@Test
	void shouldFindAllTasksOfInternalFlowAtStart() {
		var workflowEstimator = new WorkflowEstimator(process, null, "internal");
		var estimatedTasks = workflowEstimator.findAllTasks(start);

		assertArrayEquals(Arrays.array("Task A", "Task C", "Task B"), getTaskNames(estimatedTasks));		
	}

	@Test
	void shouldFindTasksOnPathOfInternalFlowAtStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "internal");
		var estimatedTasks = workflowEstimator.findTasksOnPath(start);

		assertArrayEquals(Arrays.array("Task A", "Task B"), getTaskNames(estimatedTasks));		
	}

	@Test
	void shouldFindTasksOnPathOfInternalFlowAtNewStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "internal");
		var estimatedTasks = workflowEstimator.findTasksOnPath(newStart);
		
		assertArrayEquals(Arrays.array("Task B"), getTaskNames(estimatedTasks));		
	}
	
	@Test
	void shouldFindAllTasksOfExternalFlowAtStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "external");
		var estimatedTasks = workflowEstimator.findTasksOnPath(start);

		assertArrayEquals(Arrays.array("Task A", "Task C", "Task B"), getTaskNames(estimatedTasks));		
	}
	
	@Test
	void shouldFindTasksOnPathOfExternalFlowAtNewStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "external");
		var estimatedTasks = workflowEstimator.findTasksOnPath(newStart);

		assertArrayEquals(Arrays.array("Task C", "Task B"), getTaskNames(estimatedTasks));		
	}
	
	@Test
	void shouldFindTasksOnPathOfMixedFlowAtStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "mixed");
		var estimatedTasks = workflowEstimator.findTasksOnPath(start);

		assertArrayEquals(Arrays.array("Task A", "Task B"), getTaskNames(estimatedTasks));		
	}
	
	@Test
	void shouldFindTasksOnPathOfMixedFlowAtNewStart() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "mixed");
		var estimatedTasks = workflowEstimator.findTasksOnPath(newStart);

		assertArrayEquals(Arrays.array("Task B"), getTaskNames(estimatedTasks));		
	}
	
	@Test
	void shouldCalculateTotalDuration() {
		var workflowEstimator = new WorkflowEstimator(process, null, null);
		Duration duration = workflowEstimator.calculateEstimatedDuration(graph.findStart());
		assertEquals(15, duration.toHours());
	}
	
	@Test
	void shouldCheckCustomInfo() throws Exception {
		var workflowEstimator = new WorkflowEstimator(process, null, "internal");
		var estimatedTasks = workflowEstimator.findTasksOnPath(newStart);
		assertEquals("abc", estimatedTasks.get(0).getCustomInfo());
	}
}