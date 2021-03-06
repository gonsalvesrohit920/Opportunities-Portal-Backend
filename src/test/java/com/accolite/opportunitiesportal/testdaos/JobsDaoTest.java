package com.accolite.opportunitiesportal.testdaos;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.JdbcTemplate;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.dao.JobsAttributeDao;
import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;


@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class JobsDaoTest {
	
	@InjectMocks
	JobsDao dao;
	
	@InjectMocks
	JobsAttributeDao attrDao;
	
	@Mock
	JobsRepository repo;
	
	
	@Mock
	JdbcTemplate template;
	
	
	
	private static Map<String, List<DropDownItem>>  dropDownAttributesList = new HashMap<>();
	
	private static Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
	
	
	private static Map<String, ChartDataObject> insightObject = new HashMap<>();
	
	private JobDescription jobDesc = new JobDescription(1, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");
	
	private List<Integer> skillList = Arrays.asList(new Integer[] {1,2,3,4,5});
	
	private JobDescriptionWithSkills jdWithSkills= new JobDescriptionWithSkills(jobDesc, skillList);
	private List<JobDescription> jdList2 = Arrays.asList(new JobDescription[] {jobDesc});
	
	private List<JobDescriptionWithSkills> jdList = Arrays.asList(new JobDescriptionWithSkills[] {jdWithSkills});
	
	public Map<String, ChartDataObject> insightMap = new HashMap<>();
	
	public List<String> mockLabels = Arrays.asList(new String[] { "Mock Label 1", "Mock Label2" });
	public List<Integer> mockData = Arrays.asList(new Integer[] { 1,2 });
	
	public List<ChartObject> mockObjects = Arrays.asList(new ChartObject[] { new ChartObject("Mock Label 1", 1),
			new ChartObject("Mock Label 2", 2)});
	
	public List<DropDownItem> ddItems = Arrays.asList(new DropDownItem[] { 
			new DropDownItem(1,"DDItem 1"),
			new DropDownItem(2,"DDItem 2")
	});
	
	Map<String, List<DropDownItem>> ddListMap = new HashMap<>();
	
	Map<String, Map<Integer, String>> ddmapMap = new HashMap<>();

	@Test
	void testGetAttributes() {
		when(repo.getItemList(JobsConstants.LOCATION)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.HIRING_MANAGERS)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.EMPLOYMENT_TYPE)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.PROFILE)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.SKILLS)).thenReturn(ddItems);
		
		Assert.assertEquals(ddItems.size(), attrDao.getAttributes().get(JobsConstants.LOCATION).size());
	}

	@Test
	void testGetAttributesMap() {
		when(repo.getItemList(JobsConstants.LOCATION)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.HIRING_MANAGERS)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.EMPLOYMENT_TYPE)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.PROFILE)).thenReturn(ddItems);
		when(repo.getItemList(JobsConstants.SKILLS)).thenReturn(ddItems);
		
		Assert.assertEquals(ddItems.get(0).getName(), attrDao.getAttributesMap().get(JobsConstants.LOCATION).get(1));
	}

	@Test
	void testSaveJobDescription() {
		when(repo.saveJobdescription(jobDesc)).thenReturn(1);
		Assert.assertEquals(1, dao.saveJobDescription(jobDesc));
	}

	@Test
	void testSaveJobSkills() {
		when(repo.saveJobDescriptionSkills(1, skillList)).thenReturn(skillList.size());
		Assert.assertEquals(skillList.size(), dao.saveJobSkills(1, skillList));
	}

	@Test
	void testFindJobDescriptionbyId() {
		when(repo.findById(1)).thenReturn(jobDesc);
		Assert.assertEquals(jobDesc, dao.findJobDescriptionbyId(1));
	}
	
	
	@Test
	void testFindJobDescriptionVersionbyId() {
		when(repo.findVersionById(1)).thenReturn(jobDesc);
		Assert.assertEquals(jobDesc, dao.findJobDescriptionVersionbyId(1));
	}

	@Test
	void testGetAllJobDescriptions() {
		when(repo.findAllJobDescriptions()).thenReturn(jdList2);
		Assert.assertEquals(jobDesc, dao.getAllJobDescriptions().get(0).getJobDescription());
	}
	
	@Test
	void getAllJobDescriptionsVersions() {
		when(repo.findAllJobDescriptionVersions(Mockito.anyInt())).thenReturn(jdList2);
		Assert.assertEquals(jobDesc, dao.getAllJobDescriptionsVersions(1).get(0).getJobDescription());
	}

	@Test
	void testGetSkillsById() {
		when(repo.getSkillListById(1)).thenReturn(skillList);
		Assert.assertEquals(skillList, dao.getSkillsById(1));
	}
	
	@Test
	void testGetVersionSkillsById() {
		when(repo.getVersionSkillListById(1)).thenReturn(skillList);
		Assert.assertEquals(skillList, dao.getVersionSkillsById(1));
	}

	@Test
	void testGetInsightMap() {
		when(repo.getSkillCounts()).thenReturn(mockObjects);
		when(repo.getLocationCounts()).thenReturn(mockObjects);
		when(repo.getHiringManagerCCounts()).thenReturn(mockObjects);
		when(repo.getProfileCounts()).thenReturn(mockObjects);
		when(repo.getResolvedSkillCounts()).thenReturn(mockObjects);
		when(repo.getEmploymentTypeCounts()).thenReturn(mockObjects);
		
		Assert.assertEquals(mockLabels.size(), attrDao.getInsightMap().get(JobsConstants.LOCATION).getLabels().size());
		Assert.assertEquals(mockLabels.size(), attrDao.getInsightMap().get(JobsConstants.HIRING_MANAGERS).getLabels().size());
		Assert.assertEquals(mockLabels.size(), attrDao.getInsightMap().get(JobsConstants.SKILLS).getLabels().size());
		Assert.assertEquals(mockLabels.size(), attrDao.getInsightMap().get(JobsConstants.PROFILE).getLabels().size());
		Assert.assertEquals(mockLabels.size(), attrDao.getInsightMap().get(JobsConstants.EMPLOYMENT_TYPE).getLabels().size());
		Assert.assertEquals(mockLabels.size(), attrDao.getInsightMap().get(JobsConstants.RESOLVED_SKILLS).getLabels().size());
	}

	@Test
	void testUpdateJobDescription() {
		when(repo.updateJobDescription(jobDesc)).thenReturn(true);
		Assert.assertEquals(true, dao.updateJobDescription(jdWithSkills));
	}

	@Test
	void testDeleteJobDescription() {
		when(repo.deleteJobDescription(1)).thenReturn(true);
		when(repo.deleteSkillsByJd(1)).thenReturn(true);
		Assert.assertEquals(true, dao.deleteJobDescription(1));
	}

	@Test
	void testResolveJobDescription() {
		when(repo.deleteJobDescription(1)).thenReturn(true);
		when(repo.deleteSkillsByJd(1)).thenReturn(true);
		Assert.assertEquals(true, dao.resolveJobDescription(1));
	}

}
