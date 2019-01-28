package com.cn.jm.core.utils.json;

/**
 * Copyright (c) 2011-2019, James Zhan 詹波 (jfinal@126.com).
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

import com.jfinal.json.IJsonFactory;
import com.jfinal.json.Json;

/**
 * IJsonFactory 的 jfinal 实现.
 */
public class MyJFinalJsonFactory implements IJsonFactory {

	private static final MyJFinalJsonFactory me = new MyJFinalJsonFactory();

	public static MyJFinalJsonFactory me() {
		return me;
	}

	public Json getJson() {
		return new MyJFinalJson();
	}
}
