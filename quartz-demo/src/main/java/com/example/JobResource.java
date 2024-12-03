package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jobs")
public class JobResource {
    private final Scheduler scheduler;

    public JobResource(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Create a job
     * 
     * @param jobRequest
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createJob(JobRequest jobRequest) {
        try {
            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity(jobRequest.getName(), jobRequest.getGroup())
                    .build();

            // Calculate the start time with delay
            long delayInSeconds = jobRequest.getDelayInSeconds();
            Date startTime = new Date(System.currentTimeMillis() + delayInSeconds * 1000);

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(jobRequest.getName() + "Trigger", jobRequest.getGroup())
                    .startAt(startTime); // Set the start time with delay

            if ("repeated".equalsIgnoreCase(jobRequest.getType())) {
                triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(jobRequest.getIntervalInSeconds())
                        .repeatForever());
            }

            Trigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(job, trigger);

            return Response.ok("Job scheduled successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error scheduling job: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{group}/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteJob(@PathParam("name") String jobName,
            @PathParam("group") String groupName) {
        try {
            JobKey jobKey = new JobKey(jobName, groupName);
            boolean deleted = scheduler.deleteJob(jobKey);

            if (deleted) {
                return Response.ok("Job deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Job not found.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting job: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobList() {
        try {
            List<String> jobList = new ArrayList<>();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    jobList.add(jobKey.getName());
                }
            }
            return Response.ok(jobList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving job list: " + e.getMessage())
                    .build();
        }
    }
}