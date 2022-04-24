package ru.apetnet.desktop.ui.parent

import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.exception.IllegalMatrixValueException
import ru.apetnet.desktop.exception.PredefinedErrorException
import ru.apetnet.desktop.util.log.safeLog
import ru.apetnet.desktop.util.resource.string
import tornadofx.Fragment
import tornadofx.View
import tornadofx.error


abstract class ParentFragment : Fragment() {
    protected fun showErrorDialog(data: ControllerErrorData) {
        showErrorDialog(data.throwable)
    }

    protected fun showErrorDialog(err: Throwable) {
        showErrorDialog(
            err = err,
            getString = { id, args ->
                if (args != null) {
                    string(id, args)
                }
                else {
                    string(id)
                }
            }
        )
    }

    protected fun showErrorDialog(err: String? = null) = showErrorDialog(
        err = err,
        getString = { id ->
            string(id)
        }
    )
}

abstract class ParentView : View() {
    protected fun showErrorDialog(data: ControllerErrorData) {
        showErrorDialog(data.throwable)
    }

    protected fun showErrorDialog(err: Throwable) {
        showErrorDialog(
            err = err,
            getString = { id, args ->
                if (args != null) {
                    string(id, args)
                }
                else {
                    string(id)
                }
            }
        )
    }

    protected fun showErrorDialog(err: String? = null) {
        showErrorDialog(
            err = err,
            getString = { id ->
                string(id)
            }
        )
    }
}

private fun showErrorDialog(
    err: Throwable,
    getString: (id: String, any: List<Any>?) -> String
) {
    if (BuildConfig.DEBUG) {
        err.printStackTrace()
    }

    val errMsg = when (err) {
        is PredefinedErrorException -> {
            getString(err.errorId, null)
        }
        is IllegalMatrixValueException -> {
            getString("container.error.illegal_matrix_value", listOf(err.value))
        }
        else -> {
            err.message
        }
    }

    showErrorDialog(
        err = errMsg,
        getString = { id ->
            getString(id, null)
        }
    )
}

private fun showErrorDialog(
    err: String? = null,
    getString: (id: String) -> String
) {
    error(
        header = "",
        title = getString("workspace.tab.fragment.dialog.error_message_title"),
        content = err ?: getString("container.error.unknown"),
        actionFn = {

        }
    )
}