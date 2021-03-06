package hudson.notify;

import java.io.IOException;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Run;
/**
 * 
 * @author tang
 */
public class HundredTimesBuildNumberSychronize extends BuildNumberSychronize {



	public HundredTimesBuildNumberSychronize(AbstractBuild<?, ?> upstrambuild,
			BuildListener listener) {
		super(upstrambuild, listener);
	}

	@Override
	int setNextDownStreamBuildNumber(AbstractBuild<?, ?> upstrambuild) {
		return 100 * upstrambuild.getNumber();
	}
	
	/**
	 * This is a hook before updateNextBuildNumber
	 * @param nextUpStreamBuildNumber
	 * @param downstreamProject
	 * @throws IOException 
	 */
	public void downstreamProjectClear(int nextUpStreamBuildNumber,
			AbstractProject downstreamProject) throws IOException {
		try{
			
			//delete the builds of downstream job which number are bigger than the upstream job
			if(downstreamProject.getNextBuildNumber()>=nextUpStreamBuildNumber){
				for(int number=nextUpStreamBuildNumber;number<=downstreamProject.getNextBuildNumber();number++){
					Run run = downstreamProject.getBuildByNumber(number);
					if(run!=null){
						getListener().getLogger().println("delete the build "+number + "of project "+downstreamProject.getName());
						run.delete();
					}
			    }
		    }
		} catch (Exception e) {
		}
	}

}
