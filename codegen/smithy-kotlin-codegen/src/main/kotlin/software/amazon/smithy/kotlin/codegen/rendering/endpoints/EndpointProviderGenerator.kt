/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package software.amazon.smithy.kotlin.codegen.rendering.endpoints

import software.amazon.smithy.codegen.core.Symbol
import software.amazon.smithy.kotlin.codegen.KotlinSettings
import software.amazon.smithy.kotlin.codegen.core.KotlinWriter
import software.amazon.smithy.kotlin.codegen.core.RuntimeTypes
import software.amazon.smithy.kotlin.codegen.core.clientName
import software.amazon.smithy.kotlin.codegen.model.buildSymbol

/**
 * Generates the endpoint provider interface.
 *
 * The default implementation of the provider is generated by [DefaultEndpointProviderGenerator].
 */
class EndpointProviderGenerator(
    private val writer: KotlinWriter,
    private val settings: KotlinSettings,
    private val providerSymbol: Symbol,
    private val paramsSymbol: Symbol,
) {
    companion object {
        fun getSymbol(settings: KotlinSettings): Symbol =
            buildSymbol {
                val prefix = clientName(settings.sdkId)
                name = "${prefix}EndpointProvider"
                namespace = "${settings.pkg.name}.endpoints"
            }
    }

    fun render() {
        renderDocumentation()
        writer.write(
            "#L fun interface #T: #T<#T>",
            settings.api.visibility,
            providerSymbol,
            RuntimeTypes.SmithyClient.Endpoints.EndpointProvider,
            paramsSymbol,
        )
    }

    private fun renderDocumentation() {
        writer.dokka {
            write("Resolves to an endpoint for a given service operation.")
        }
    }
}
