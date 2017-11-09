package com.py.coordinate.core.requester;

import com.py.model.AddressInfo;
import com.py.model.baidu.Location;

/**
 * @author panfenghui
 *
 */
public  interface CoordinateRequester {

	Location getLocation(AddressInfo addressInfo);

}
