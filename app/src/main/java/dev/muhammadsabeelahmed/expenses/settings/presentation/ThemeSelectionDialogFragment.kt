package dev.muhammadsabeelahmed.expenses.settings.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.muhammadsabeelahmed.expenses.R
import dev.muhammadsabeelahmed.expenses.common.presentation.Theme

class ThemeSelectionDialogFragment : DialogFragment(), DialogInterface.OnClickListener {

    var onThemeSelected: ((Theme) -> Unit)? = null

    private val themes by lazy { Theme.values() }

    private lateinit var currentTheme: Theme

    private var selectedTheme: Theme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentTheme = arguments?.getString(ARGUMENT_CURRENT_THEME)
            ?.let { Theme.valueOf(it) } ?: Theme.SYSTEM_DEFAULT
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.select_theme)
            .setSingleChoiceItems(getItems(), getCheckedItem(), this)
            .setPositiveButton(R.string.ok) { _, _ ->
                selectedTheme?.let { onThemeSelected?.invoke(it) }
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    private fun getItems(): Array<String> {
        return themes.map { theme ->
            when (theme) {
                Theme.LIGHT -> requireContext().getString(R.string.light)
                Theme.DARK -> requireContext().getString(R.string.dark)
                Theme.SYSTEM_DEFAULT -> requireContext().getString(R.string.system_default)
            }
        }.toTypedArray()
    }

    private fun getCheckedItem(): Int = themes.indexOf(currentTheme)

    override fun onClick(dialog: DialogInterface, which: Int) {
        selectedTheme = themes[which]
    }

    companion object {
        const val TAG = "ThemeSelectionDialogFragment"

        private const val ARGUMENT_CURRENT_THEME =
            "dev.muhammadsabeelahmed.expenses.ARGUMENT_CURRENT_THEME"

        fun newInstance(currentTheme: Theme): ThemeSelectionDialogFragment {
            val arguments = Bundle().apply {
                putString(ARGUMENT_CURRENT_THEME, currentTheme.name)
            }

            return ThemeSelectionDialogFragment().apply { this.arguments = arguments }
        }
    }
}