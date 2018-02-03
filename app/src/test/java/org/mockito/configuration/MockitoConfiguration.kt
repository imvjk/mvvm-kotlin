package org.mockito.configuration

/**
 * Copyright (C) VijayK
 */
class MockitoConfiguration : DefaultMockitoConfiguration() {

    override fun enableClassCache(): Boolean {
        return false
    }
}