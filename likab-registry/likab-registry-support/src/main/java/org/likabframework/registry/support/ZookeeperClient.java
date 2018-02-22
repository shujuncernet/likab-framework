/**
 * Copyright 2017-2020.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.likabframework.registry.support;


import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * ClassName:ZookeeperClient
 * @author sxp
 * @date:2017年4月16日 下午1:51:13
 */
public class ZookeeperClient {
	
	private final ZkClient client;
	
	private static final Log logger = LogFactory.getLog(ZookeeperClient.class);
	
	private List<String> childrenList;

	/**
	 * @param serverAddress
	 */
	public ZookeeperClient(final String serverAddress) {
		client = new ZkClient(serverAddress);
		client.subscribeStateChanges(new IZkStateListener() {
			
			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				if (state == KeeperState.SyncConnected) {
					logger.info("zookeeper address '" + serverAddress + "'connect successfully");
				} else if (state == KeeperState.Disconnected) {
					logger.info("zookeeper address '" + serverAddress + "'disconnect");
				} else if (state == KeeperState.AuthFailed) {
					logger.info("zookeeper address '" + serverAddress + "'auth failed");
				} else if (state == KeeperState.Expired) {
					logger.info("zookeeper address '" + serverAddress + "'expired");
				}
			}
			
			@Override
			public void handleNewSession() throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * createEphemeral
	 * void
	 */
	public void createEphemeral(String path) {
		try {
			client.createEphemeral(path);
		} catch (ZkNodeExistsException e) {
			logger.error("create ephemeral path '" + path + "' fail");
			throw new RuntimeException("create ephemeral path '" + path + "' fail", e);
		}
	}
	
	/**
	 * createEphemeral
	 * void
	 */
	public void createEphemeral(String path, String data, int createMode) {
		try {
			CreateMode mode = CreateMode.fromFlag(createMode);
			client.create(path, data, mode);
		} catch (Exception e) {
			logger.error("create ephemeral path '" + path + "' fail");
			throw new RuntimeException("create Ephemeral znode fail", e);
		}
	}
	
	/**
	 * createPersistent
	 * void
	 */
	public void createPersistent(String path) {
		createPersistent(path, false);
	}
	
	/**
	 * createPersistent
	 * void
	 */
	public void createPersistent(String path, boolean createParents) {
		client.createPersistent(path, createParents);
	}
	
	/**
	 * exsistPath
	 * boolean
	 */
	public boolean exsistPath(String path) {
		return client.exists(path);
	}
	
	/**
	 * getChildren
	 * List<String>
	 */
	public List<String> getChildren(String path) {
		childrenList = client.getChildren(path);
		client.subscribeChildChanges(path, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds)
					throws Exception {
				logger.info("znode:" + parentPath + "childrens changed!");
				childrenList = currentChilds;
			}
		});
		if(childrenList == null || childrenList.isEmpty()) {
			throw new IllegalArgumentException("There is no list of services available");
		}
		return childrenList;
	}
	
	/**
	 * getDataFromZnode
	 * String
	 */
	public String getDataFromZnode(String path) {
		return client.readData(path);
	}
	
}
