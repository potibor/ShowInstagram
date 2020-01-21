package com.hsnozan.basemvvm.core

import android.app.Application
import com.hsnozan.basemvvm.api.BaseService

open class BaseRepo(val baseService: BaseService, val application : Application)