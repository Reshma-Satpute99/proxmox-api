package com.proxmox.test.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.proxmox.test.controller.Controller.ResizeDiskRequest;
import com.proxmox.test.entity.ProxmoxDetails;

public interface SaveProxmox {

	ResponseEntity<String> createVM(ProxmoxDetails proxmoxDetails) throws Exception;

    public void resizeVmDisk(@PathVariable Long vmId, @RequestBody ResizeDiskRequest payload) ;

}
