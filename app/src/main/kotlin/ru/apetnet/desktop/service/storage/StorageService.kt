package ru.apetnet.desktop.service.storage

import ru.apetnet.desktop.domain.service.storage.LoadedProjectInfo
import ru.apetnet.desktop.domain.service.storage.SavedProjectInfo
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.pnutil.storagemanager.model.ProjectData
import java.io.File

interface StorageService {
    fun saveProject(
        file: File,
        data: ProjectData<WorkspaceObject>
    ): SavedProjectInfo

    fun loadProject(
        file: File
    ): LoadedProjectInfo
}