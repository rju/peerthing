/**
 * 
 */
package largeDataAggregator;

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * @author prefec2
 *
 */
public class NormalFile implements IFile {
	
	private IPath path;
	
	public NormalFile(String fileName) {
		this.path=new Path(fileName);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#appendContents(java.io.InputStream, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void appendContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.appendContents not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#appendContents(java.io.InputStream, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void appendContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.appendContents not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#create(java.io.InputStream, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void create(InputStream source, boolean force,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.create not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#create(java.io.InputStream, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void create(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.create not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#createLink(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void createLink(IPath localLocation, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.createLink not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#delete(boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.delete not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getCharset()
	 */
	public String getCharset() throws CoreException {
		throw new RuntimeException("NormalFile.getCharset not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getCharset(boolean)
	 */
	public String getCharset(boolean checkImplicit) throws CoreException {
		throw new RuntimeException("NormalFile.getCharset not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getCharsetFor(java.io.Reader)
	 */
	public String getCharsetFor(Reader reader) throws CoreException {
		throw new RuntimeException("NormalFile.getCharsetFor not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getContentDescription()
	 */
	public IContentDescription getContentDescription() throws CoreException {
		throw new RuntimeException("NormalFile.getContentDescription not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getContents()
	 */
	public InputStream getContents() throws CoreException {
		throw new RuntimeException("NormalFile.getContents not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getContents(boolean)
	 */
	public InputStream getContents(boolean force) throws CoreException {
		throw new RuntimeException("NormalFile.getContents not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getEncoding()
	 */
	public int getEncoding() throws CoreException {
		throw new RuntimeException("NormalFile.getEncoding not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getFullPath()
	 */
	public IPath getFullPath() {
		return this.path;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getHistory(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IFileState[] getHistory(IProgressMonitor monitor)
			throws CoreException {
		throw new RuntimeException("NormalFile.getHistory not implemented");
		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getName()
	 */
	public String getName() {
		return this.path.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#isReadOnly()
	 */
	public boolean isReadOnly() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#move(org.eclipse.core.runtime.IPath, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IPath destination, boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.move not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setCharset(java.lang.String)
	 */
	public void setCharset(String newCharset) throws CoreException {
		throw new RuntimeException("NormalFile.setCharset not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setCharset(java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setCharset(String newCharset, IProgressMonitor monitor)
			throws CoreException {
		throw new RuntimeException("NormalFile.setCharset not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(java.io.InputStream, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.setContents not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(org.eclipse.core.resources.IFileState, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(IFileState source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.setContents not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(java.io.InputStream, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.setContents not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(org.eclipse.core.resources.IFileState, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(IFileState source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.setContents not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceProxyVisitor, int)
	 */
	public void accept(IResourceProxyVisitor visitor, int memberFlags)
			throws CoreException {
		throw new RuntimeException("NormalFile.accept not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor)
	 */
	public void accept(IResourceVisitor visitor) throws CoreException {
		throw new RuntimeException("NormalFile.accept not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor, int, boolean)
	 */
	public void accept(IResourceVisitor visitor, int depth,
			boolean includePhantoms) throws CoreException {
		throw new RuntimeException("NormalFile.accept not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor, int, int)
	 */
	public void accept(IResourceVisitor visitor, int depth, int memberFlags)
			throws CoreException {
		throw new RuntimeException("NormalFile.accept not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#clearHistory(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void clearHistory(IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.clearHistory not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IPath destination, boolean force, IProgressMonitor monitor)
			throws CoreException {
		throw new RuntimeException("NormalFile.copy not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IPath destination, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.copy not implemented");


	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IProjectDescription description, boolean force,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.copy not implemented");


	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IProjectDescription description, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.copy not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#createMarker(java.lang.String)
	 */
	public IMarker createMarker(String type) throws CoreException {
		throw new RuntimeException("NormalFile.createMarker not implemented");

		// return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#delete(boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(boolean force, IProgressMonitor monitor)
			throws CoreException {
		throw new RuntimeException("NormalFile.delete not implemented");


	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#delete(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(int updateFlags, IProgressMonitor monitor)
			throws CoreException {
		throw new RuntimeException("NormalFile.delete not implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#deleteMarkers(java.lang.String, boolean, int)
	 */
	public void deleteMarkers(String type, boolean includeSubtypes, int depth)
			throws CoreException {
		throw new RuntimeException("NormalFile.deleteMarkers not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#exists()
	 */
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#findMarker(long)
	 */
	public IMarker findMarker(long id) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#findMarkers(java.lang.String, boolean, int)
	 */
	public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getFileExtension()
	 */
	public String getFileExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getLocalTimeStamp()
	 */
	public long getLocalTimeStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getLocation()
	 */
	public IPath getLocation() {
		return this.path;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getMarker(long)
	 */
	public IMarker getMarker(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getModificationStamp()
	 */
	public long getModificationStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getParent()
	 */
	public IContainer getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getPersistentProperty(org.eclipse.core.runtime.QualifiedName)
	 */
	public String getPersistentProperty(QualifiedName key) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getProject()
	 */
	public IProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getProjectRelativePath()
	 */
	public IPath getProjectRelativePath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getRawLocation()
	 */
	public IPath getRawLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getResourceAttributes()
	 */
	public ResourceAttributes getResourceAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getSessionProperty(org.eclipse.core.runtime.QualifiedName)
	 */
	public Object getSessionProperty(QualifiedName key) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getType()
	 */
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getWorkspace()
	 */
	public IWorkspace getWorkspace() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isAccessible()
	 */
	public boolean isAccessible() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isDerived()
	 */
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isLocal(int)
	 */
	public boolean isLocal(int depth) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isLinked()
	 */
	public boolean isLinked() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isPhantom()
	 */
	public boolean isPhantom() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isSynchronized(int)
	 */
	public boolean isSynchronized(int depth) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isTeamPrivateMember()
	 */
	public boolean isTeamPrivateMember() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IPath destination, boolean force, IProgressMonitor monitor)
			throws CoreException {
		throw new RuntimeException("NormalFile.move not implemented");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IPath destination, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.move not implemented");


	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IProjectDescription description, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.move not implemented");


	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IProjectDescription description, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("NormalFile.move not implemented");


	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#refreshLocal(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void refreshLocal(int depth, IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#revertModificationStamp(long)
	 */
	public void revertModificationStamp(long value) throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setDerived(boolean)
	 */
	public void setDerived(boolean isDerived) throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setLocal(boolean, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setLocal(boolean flag, int depth, IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setLocalTimeStamp(long)
	 */
	public long setLocalTimeStamp(long value) throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setPersistentProperty(org.eclipse.core.runtime.QualifiedName, java.lang.String)
	 */
	public void setPersistentProperty(QualifiedName key, String value)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setReadOnly(boolean)
	 */
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setResourceAttributes(org.eclipse.core.resources.ResourceAttributes)
	 */
	public void setResourceAttributes(ResourceAttributes attributes)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setSessionProperty(org.eclipse.core.runtime.QualifiedName, java.lang.Object)
	 */
	public void setSessionProperty(QualifiedName key, Object value)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setTeamPrivateMember(boolean)
	 */
	public void setTeamPrivateMember(boolean isTeamPrivate)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#touch(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void touch(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
	 */
	public boolean contains(ISchedulingRule rule) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
	 */
	public boolean isConflicting(ISchedulingRule rule) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IResourceProxy createProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findMaxProblemSeverity(String type, boolean includeSubtypes, int depth) throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URI getLocationURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPathVariableManager getPathVariableManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<QualifiedName, String> getPersistentProperties() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getRawLocationURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<QualifiedName, Object> getSessionProperties() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDerived(int options) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden(int options) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVirtual() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLinked(int options) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTeamPrivateMember(int options) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDerived(boolean isDerived, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean isHidden) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createLink(URI location, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		
	}

}
