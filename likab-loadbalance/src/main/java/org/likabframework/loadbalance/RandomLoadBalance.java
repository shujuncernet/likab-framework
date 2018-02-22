package org.likabframework.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * ClassName:RandomLoadBalance
 * @author sxp
 * @date:2017年5月7日 下午6:05:49
 */
public class RandomLoadBalance implements LoadBalance {

	/* (non-Javadoc)
	 * @see org.likabframework.loadbalance.LoadBalance
	 * @see #loadBalance(java.lang.String, java.util.List)
	 */
	@Override
	public String loadBalance(String remoteIP, List<String> serverList) {
		Random random = new Random();
		int randomPos = random.nextInt(serverList.size());
		return serverList.get(randomPos);
	}

	/* (non-Javadoc)
	 * @see org.likabframework.loadbalance.LoadBalance#getScheme()
	 */
	@Override
	public String getScheme() {
		return RANDOM_LOADBALANCE;
	}
}
