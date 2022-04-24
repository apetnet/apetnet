package ru.apetnet.desktop.service.storage

import com.google.inject.Inject
import ru.apetnet.desktop.domain.service.storage.LoadedProjectInfo
import ru.apetnet.desktop.domain.service.storage.SavedProjectInfo
import ru.apetnet.desktop.domain.service.storage.mapToStorageData
import ru.apetnet.desktop.domain.service.storage.mapToWorkspaceObject
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.desktop.exception.UnsupportedExtensionException
import ru.apetnet.pnutil.storagemanager.StorageManager
import ru.apetnet.pnutil.storagemanager.model.ProjectData
import java.io.File

class StorageServiceImpl @Inject constructor(
    private val storageManager: StorageManager
) : StorageService {
    override fun saveProject(file: File, data: ProjectData<WorkspaceObject>): SavedProjectInfo {
        return when (val ext = file.extension) {
            "json" -> {
                storageManager.save(
                    file = file,
                    data = mapToStorageData(
                        id = data.id,
                        values = data.items
                    )
                )
                SavedProjectInfo(file.name)
            }
            else -> throw UnsupportedExtensionException(ext)
        }
    }

    override fun loadProject(file: File): LoadedProjectInfo {
        when (val ext = file.extension) {
            "json" -> {
                val data = mapToWorkspaceObject(
                    data = storageManager.read(file)
                )
                return LoadedProjectInfo(
                    id = data.id,
                    file = file,
                    items = data.items
                )
            }
            else -> throw UnsupportedExtensionException(ext)
        }
    }
}